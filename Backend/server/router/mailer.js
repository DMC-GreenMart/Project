
const nodemailer = require('nodemailer');

function sendMail(email , data)
{
  //
  // console.log(data)
  const dataString = JSON.stringify(data);
//transporter

const transporter= nodemailer.createTransport({
service:"gmail",
auth:{
  user:"testing.tanmay123@gmail.com",
  pass:"hjmz ibxl zsux poqq"
}
})

const info={
from:"testing.tanmay123@gmail.com",
to:email,
subject:" Green Mart Sabzii Wale",
text:dataString
}

transporter.sendMail(info,(err,result)=>{
if(err){
  console.log("Error in sending Mail",err);
}
else{
  console.log("Mail sent successfully",info);
}
})
} 

module.exports = sendMail;