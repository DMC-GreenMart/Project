const express = require("express")
const db = require("../database/db")
const utils = require("../utils")
const sendMail = require("./mailer")

const router = express.Router()

// Placeholder data structure to store OTPs (in-memory storage, not suitable for production)
const otpStorage = {};

// Function to generate and store an OTP for a given username
function generateOTP(username) {
  const otp = Math.floor(1000 + Math.random() * 9000);
  otpStorage[username] = otp.toString().padStart(4, '0');
  return otp;
}

// Function to verify if the entered OTP matches the stored OTP for a given username
function verifyOTP(username, enteredOTP) {
  const storedOTP = otpStorage[username];
  return storedOTP && storedOTP === enteredOTP;
}

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
  const { username,  email, password } = request.body
  const sql =
    "INSERT INTO users(username,email,password) VALUES(?,?,?)"
  db.query(
    sql,
    [ username,  email, password],
    (error, data) => {

      if(error == null)
      {
        let otp = generateOTP(username)
        sendMail(email , {otp})
        
        data = "Registration successful. OTP sent to your email."
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

router.post('/verify-otp', (req, res) => {
  // Extract data from the request body
  const { username, otp } = req.body;
 
  let message ;
  let error ;
  // Your OTP verification logic goes here
  if (verifyOTP(username, otp)) {
     message = " OTP verification successful "

  } else {
  error = "message: 'Invalid OTP. Please try again.' }"

  }

res.send(utils.createResult(error, message))


  
});



// http://localhost:9898/user/address
// edit Profile Remaining

module.exports = router
