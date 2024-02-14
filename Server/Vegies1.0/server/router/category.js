const express = require("express")
const db = require("../database/db")
const utils = require("../utils")

const router = express.Router()
// get by id
router.get("/", (request, response) => {
  const sql = "SELECT * FROM category"
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

 // delete by id
router.delete("/:id", (request, response) => {
  const sql = "delete FROM category WHERE category_id=? "
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})
// edit by id
router.put("/:id", (request, response) => {
  const { category_name,  category_image } = request.body
console.log(category_image , category_name);
  const sql =
    "update  category set category_image=? , category_name=? where category_id=?"
  db.query(
    sql,
    [  category_image , category_name ,request.params.id],
    (error, data) => {
      response.send(utils.createResult(error, data))
    }
  )
})

// from admin
router.get("/category/:id", (request, response) => {
  const sql = "SELECT * FROM category WHERE category_id=?"
  db.query(sql, [request.params.id], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

module.exports = router