const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()

// router.get("/", (request, response) => {
//     const sql = "SELECT * FROM cart"
//     db.query(sql, request.params.id, (error, data) => {
//       response.send(utils.createResult(error, data))
//     })
//   })

// get in cart manner 
router.get("/:id", (request, response) => {
  let userId = request.params.id
    const sql = `SELECT
    cart.cart_id,
    products.product_name,
    products.product_id,
    products.price AS ProductPrice,
    cart.price AS TotalPrice,
    products.image,
    cart.quantity
FROM
    cart
JOIN
    products ON cart.product_id = products.product_id
WHERE
    cart.user_id =${userId};`

    db.query(sql, request.params.id, (error, data) => {
      response.send(utils.createResult(error, data))
    })
  })


// router.get("/product/:id", (request, response) => {
//   const sql = "SELECT * FROM cart WHERE user_id=?"
//   db.query(sql, request.params.id, (error, data) => {
//     response.send(utils.createResult(error, data))
//   })
// })

// add to cart
router.post("/addcart", (request, response) => {
  const {user_id, product_id, quantity , price} = request.body
  const sql =
    "INSERT INTO cart(user_id, product_id, quantity , price) VALUES(?,?,?,?)"
  db.query(
    sql,
    [ user_id, product_id, quantity , price],
    (error, data) => {
      response.send(utils.createResult(error, data))
    }
  )
})


router.put("/inc/:pid", (request, response) => {
    const {price, cart_id} = request.body
    const pid = request.params.pid;
    const sql = `UPDATE cart
    SET quantity = quantity + 1,
        price = price + ?
    WHERE cart_id= ? AND product_id = ?;`
    db.query(
      sql,
      [  price , cart_id , pid ] ,
      (error, data) => {
        response.send(utils.createResult(error, data))
      }
    )
  })

  router.put("/dec/:pid", (request, response) => {
    const {price, cart_id} = request.body
    const pid = request.params.pid;
    const sql = `UPDATE cart
    SET quantity = quantity - 1,
        price = price - ?
    WHERE cart_id= ? AND product_id = ? and quantity >0;`
    db.query(
      sql,
      [  price , cart_id , pid ] ,
      (error, data) => {
        response.send(utils.createResult(error, data))
      }
    )
  })


  router.delete("/:id", (request, response) => {
    let id =request.params.id;
    const sql = "delete FROM cart where cart_id = ?";
    db.query(sql,id, (error, data) => {
      response.send(utils.createResult(error, data))
    })
  })

// router.post("/login", (request, response) => {
//   const { email, password } = request.body
//   const sql = "SELECT * FROM users WHERE email=? AND password=?"
//   db.query(sql, [email, password], (error, data) => {
//     response.send(utils.createResult(error, data))
//   })
// })

module.exports = router