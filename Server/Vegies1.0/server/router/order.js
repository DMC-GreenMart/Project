const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()
const sendMail = require("./mailer")
// admin command'
router.get("/", (request, response) => {
  const sql = "SELECT * FROM orders"
  db.query(sql,  (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

router.get("/myorderDetail/:id", (request, response) => {
  const sql = `SELECT
  o.order_id,
  o.order_date,
  p.product_name,
  p.price,
  od.quantity,
  (p.price * od.quantity) AS total_price
FROM
  order_details od
JOIN
  orders o ON od.order_id = o.order_id
JOIN
  products p ON od.product_id = p.product_id
WHERE
  o.user_id = ?  -- Replace ? with the user ID you want to filter by
ORDER BY
  o.order_id DESC`

  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

// get by id bill generate bill ;
router.get("/bill/:id", (request, response) => { 
          let id =  request.params.id
    const sql = ` SELECT
    orders.order_id,
    orders.order_date,
     products.product_name,
    order_details.quantity,
    order_details.price AS unit_price,
      addresses.street AS delivery_street,
    addresses.city AS delivery_city
FROM
    orders
JOIN
    order_details ON orders.order_id = order_details.order_id
JOIN
    addresses ON orders.address_id = addresses.address_id
    JOIN
    products ON order_details.product_id = products.product_id
WHERE
    orders.order_id =${id}; `
    db.query(sql ,(error, data) => {

             if(error == null)
             {
              sendMail("tanmaysaxena206@gmail.com" , data)
             }
      response.send(utils.createResult(error, data))
    })
  })
  
module.exports = router
