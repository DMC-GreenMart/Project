const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()

router.get("/", (request, response) => {
  const sql = "SELECT * FROM addresses"
  db.query(sql, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

router.post("/", (request, response) => {
  const { user_id, street, city, state, zip_code } = request.body
  const sql =
    "INSERT INTO addresses(user_id, street, city, state, zip_code ) VALUES(?,?,?,?,?)"
  db.query(sql, [user_id, street, city, state, zip_code], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})





module.exports = router
