var aws = require("aws-sdk");
var ses = new aws.SES({ region: "us-east-1" });

exports.handler = async function(event) {
    var params = {
        Destination: {
            ToAddresses: ["meghdut.choudhury+iiht@live.com"]
        },
        Message: {
            Body: {
                Text: { Data: "deleteProduct() was triggered" },
            },

            Subject: { Data: "deleteProduct() Trigger Email" },
        },
        Source: "meghdut.choudhury+iiht@live.com",
    };
    console.log('sending email')
    console.log(params)
    return ses.sendEmail(params).promise()
};