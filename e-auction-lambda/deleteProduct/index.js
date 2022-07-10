const mongoose = require("mongoose");

exports.handler = async(event) => {
    console.log('entered handler')

    await sendNotification();
    let response = await process(event.pathParameters.productId);
    if (response.statusCode >= 400) {
        await callStepFunctionForFailure(response);
    }

    console.log('completed handler with response value ' + response)
    return response;
};

var process = async(productId) => {

    var response = { statusCode: null, body: null };

    if (!productId || productId.length != 24) {
        response.statusCode = 400
        response.body = '{"errorMessage": "Product ID is not in valid format"}'
        return response
    }

    mongoose
        .connect('mongodb://username:password@e-auction.cluster-cwmnescyin50.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false', {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });

    console.log("connected to db status: " + mongoose.connection.readyState);

    var retries = 0;
    while (mongoose.connection.readyState != 1 && ++retries < 20) {
        await new Promise(resolve => setTimeout(resolve, 100));
    }

    if (mongoose.connection.readyState != 1) {
        response.statusCode = 500
        response.body = '{"errorMessage": "Unable to connect to database"}'
        return response
    }

    console.log("creating schema");
    const productSchema = new mongoose.Schema({
        id: {
            type: String,
            required: true
        },
        bidEndDate: {
            type: Date,
            required: true
        }
    });
    const bidSchema = new mongoose.Schema({
        id: {
            type: String,
            required: true
        },
        productId: {
            type: String,
            required: true
        }
    });

    console.log("creating model");
    let productModel = mongoose.models.products ? mongoose.model("products") : mongoose.model("products", productSchema);
    let bidModel = mongoose.models.bids ? mongoose.model("bids") : mongoose.model("bids", bidSchema);

    let product = await productModel.findById(productId).exec();
    let bids = await bidModel.find({ productId: productId }).exec();

    //console.log(product)
    //console.log(bids)

    console.log("performing validations and deleting product");
    if (!product || !product._id) {
        response.statusCode = 400
        response.body = '{"errorMessage": "Product not found"}'
    } else if (product.bidEndDate && new Date(product.bidEndDate).getTime() < new Date().getTime()) {
        response.statusCode = 400
        response.body = '{"errorMessage": "Can not delete product after bid end date"}'
    } else if (bids && bids.length > 0) {
        response.statusCode = 400
        response.body = '{"errorMessage": "Can not delete product since a bid has already been placed on it"}'
    } else {
        response.statusCode = 200
        response.body = '{"message": "Successfully deleted product"}'
        console.log("product is eligible for deletion");
        await productModel.deleteOne({ _id: productId }).exec();
    }

    mongoose.connection.close()

    return response
}

var sendNotification = async() => {
    var AWS = require('aws-sdk');
    AWS.config.update({ region: 'us-east-1' });

    var params = {
        Message: 'deleteProduct() called',
        TopicArn: 'arn:aws:sns:us-east-1:043953693619:delete-product-notification-topic'
    };

    var publishTextPromise = new AWS.SNS({ apiVersion: '2010-03-31' }).publish(params).promise();
    await publishTextPromise;

    publishTextPromise.then(
        function(data) {
            console.log(`Message ${params.Message} sent to the topic ${params.TopicArn}`);
            console.log("MessageID is " + data.MessageId);
        }).catch(
        function(err) {
            console.error(err, err.stack);
        });
}

var callStepFunctionForFailure = async(response) => {
    const AWS = require('aws-sdk');
    const stepFunctions = new AWS.StepFunctions();

    const params = {
        stateMachineArn: 'arn:aws:states:us-east-1:043953693619:stateMachine:deleteProduct-state-machine',
        input: JSON.stringify(response),
        name: 'deleteProduct-state-machine-' + new Date().getTime()
    }

    await stepFunctions.startExecution(params, (err, data) => {
        if (err) {
            console.log(err);
        } else {
            console.log(data);
        }
    }).promise()
}