const express = require("express")
// const db = require("../database/db")
const db = require("../db")
const utils = require("../utils")

const router = express.Router()

// admin command'
router.get("/", (request, response) => {
  const sql = "SELECT * FROM orders"
  db.query(sql,  (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

 // admin for change Order Status
 router.put('/updateStatus/:orderId', (req, response) => {
  const orderId = req.params.orderId;
  const newStatus = req.body.status; // Assuming the new status is sent in the request body

  const sql = `UPDATE orders SET status = ? WHERE order_id = ?`;
  db.query(sql, [newStatus, orderId], (error, data) => {
    
    response.send(utils.createResult(error, data))
     
    })

});
// admin for orders 

router.get("/adminOrder", (request, response) => {
  const sql = `SELECT
  o.order_id,
  u.username,
  o.order_date,
  o.status
FROM
  orders o
JOIN
  users u ON o.user_id = u.user_id;
`
  db.query(sql,  (error, data) => {
    response.send(utils.createResult(error, data))
  })
})


router.get("/myorderDetail/:id", (request, response) => {
  const sql = `SELECT
  o.order_id,
  o.order_date,
  p.product_name,
  p.image,
  p.price,
  od.quantity,
  o.status,
  (p.price * od.quantity) AS total_price
FROM
  order_details od
JOIN
  orders o ON od.order_id = o.order_id
JOIN
  products p ON od.product_id = p.product_id
WHERE
  o.user_id = ?
  
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

      response.send(utils.createResult(error, data))
    })
  })
  
module.exports = router
