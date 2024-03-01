const express = require("express")
const db = require("../db")
const utils = require("../utils")
const sendMail = require("./mailer")

const router = express.Router()
const otpStorage = {};


function generateOTP() {
  const otp = Math.floor(1000 + Math.random() * 9000);
  otpStorage["xyz"] = otp.toString().padStart(4, '0');
  return otp;
}


function verifyOTP(username, enteredOTP) {
  const storedOTP = otpStorage["xyz"];

  console.log(storedOTP , enteredOTP);
  return storedOTP && storedOTP === enteredOTP;
}



router.post('/verifyotp', (req, res) => {
  console.log("request aai in the  verifyOtp");

  // Extract data from the request body
  const { username, otp } = req.body;
 
        console.log(req.body);
  let message ;
  let error  ;


  // Your OTP verification logic goes here
  if (verifyOTP(username, otp)) {
     message = " OTP verification successful "

     console.log("message")

  } else {
  error = "message: 'Invalid OTP. Please try again.' }"
  console.log(error)

  }

  
res.send(utils.createResult(error, message))
});

router.get("/", (request, response) => {
  const sql = "SELECT * FROM users"
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

// user + address
router.get("/usr/:id", (request, response) => {
  const sql =`SELECT u.user_id, u.username, a.street, a.city, a.state
  FROM users as u
  JOIN addresses as a ON a.user_id = u.user_id
  WHERE u.user_id = ?
  `
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})
router.get("/:id", (request, response) => {
  const sql = "SELECT * FROM users WHERE user_id=?"
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

router.post("/register", (request, response) => {
  const { username,  email, password ,mobile , role } = request.body
  const sql =
    "INSERT INTO users(username,email,password ,mobile , role ) VALUES(? , ?,?,?, ?)"
  db.query(
    sql,
    [ username,  email, password , mobile , role],
    (error, data) => {

      if(error == null)
      {
        let otp = generateOTP()
        console.log(otp , email);
        sendMail(email , "Your Otp is ------   "+otp)
      }
      
      response.send(utils.createResult(error, data))
    }
  )
})

router.post("/login", (request, response) => {
  const { email, password } = request.body
  const sql = "SELECT * FROM users WHERE email=? AND password=?"
  db.query(sql, [email, password], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})


router.delete("/:id", (request, response) => {
  const sql = "delete  FROM users where user_id=? "
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})
// add address for particualr user 


router.post("/address", (request, response) => {
  const { user_id, street, city, state, zip_code} = request.body
  const sql = `INSERT INTO addresses (user_id, street, city, state, zip_code)
  VALUES (?, ?, ?, ?, ?);`

  db.query(sql, [user_id, street, city, state, zip_code], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})


router.post("/:totalAmt", (request, response) => {
 
let email = request.body.email;
let totalAmt = request.params.totalAmt

       sendMail(email , "Your total amount is "+totalAmt);
       response.end();

})











module.exports = router
