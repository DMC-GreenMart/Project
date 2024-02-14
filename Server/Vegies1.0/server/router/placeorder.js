const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()

router.post("/", (request, response) => {
  const {user_id, total_amount  ,address_id} = request.body
  const sql =`
  INSERT INTO orders (user_id, total_amount , order_date ,address_id) VALUES (?, ?, current_date(),?);
SET @orderId = LAST_INSERT_ID();
select @orderID;
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT @orderId, product_id, quantity, price FROM cart WHERE user_id =${user_id};
DELETE FROM cart WHERE user_id =${user_id};
`
  db.query(
    sql,
    [ user_id, total_amount ,address_id],
    (error, data) => {
      response.send(utils.createResult(error, data))
    }
  )
})




// router.post("/login", (request, response) => {
//   const { email, password } = request.body
//   const sql = "SELECT * FROM users WHERE email=? AND password=?"
//   db.query(sql, [email, password], (error, data) => {
//     response.send(utils.createResult(error, data))
//   })
// })




module.exports = router
