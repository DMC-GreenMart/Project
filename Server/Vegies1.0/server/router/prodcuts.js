const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()

router.get("/", (request, response) => {
  console.log("request wa")
  const sql = `SELECT
  p.product_id,
  p.category_id,
  c.category_name,
  p.product_name,
  p.detail,
  p.Price,
  p.image,
  p.extras
FROM
  products p
JOIN
  category c ON p.category_id = c.category_id;
`
  db.query(sql, (error, data) => {
  console.log("request wa")

    response.send(utils.createResult(error, data))
  })
})



 // get by category id

//  router.get("/:id", (request, response) => {
//   let id =request.params.id;
//   const sql = "SELECT * FROM products where category_id = ?";
//   db.query(sql,id, (error, data) => {
//     response.send(utils.createResult(error, data))
//   })
// })
// delete products

router.delete("/:id", (request, response) => {
  let id =request.params.id;
  const sql = "delete FROM products where product_id = ?";
  db.query(sql,id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

 // get by category id

 router.get("/:id", (request, response) => {
  let id =request.params.id;
  const sql = "SELECT * FROM products where category_id = ?";
  db.query(sql,id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})


//  add Products admin 

router.post("/add", (request, response) => {
  const {category_id,product_name, price, detail , image} = request.body
  const sql = "INSERT INTO products (category_id,product_name, price, detail , image) VALUES ( ?,?,?,?,?)"
  db.query(sql, [email, password], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

// INSERT INTO products (category_id,product_name, price, detail , image) VALUES ( 1,'Lady Finger', 45.99, "500gm * 2","https://rb.gy/3qh22s");

module.exports = router
