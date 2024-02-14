const nodemailer=require('nodemailer');

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
    to:"coderbypassion702@gmail.com",
    subject:"Nodemailer email application",
    text:`Welcome to my codeserver youtube channel
            please subscribe it`
  }

  transporter.sendMail(info,(err,result)=>{
    if(err){
        console.log("Error in sending Mail",err);
    }
    else{
        console.log("Mail sent successfully",info);
    }
  })