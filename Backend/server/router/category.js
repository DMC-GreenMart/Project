const express = require("express")
const multer = require('multer')
// const db = require("../database/db")
const db = require("../db")
const utils = require("../utils")
const upload = multer({dest: 'uploads'})
const router = express.Router()
// get all category
router.get("/", (request, response) => {
  const sql = "SELECT * FROM category"
  db.query(sql, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

// get by id

router.get("/:id", (request, response) => {
  // let id = request.params.id;
  const sql = `SELECT * FROM category where category_id = ?`
  db.query(sql, request.params.id, (error, data) => {
    response.send(utils.createResult(error, data))
  })
})
// post category

router.post('/' , upload.single('image') , 
(request ,response) => {

    let { name } = request.body
    let sql= `insert into category ( category_name , category_image ) values (? , ?)`
    

    db.query(sql ,[ name  ,  request.file.filename] , (error , data) =>{
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
router.put("/:id", upload.single('category_image'), (request, response) => {
  const { category_name } = request.body;
  const category_image = request.file.filename; // Retrieve uploaded file name

  const sql = "UPDATE category SET category_name=?, category_image=? WHERE category_id=?";
  const values = [category_name, category_image, request.params.id];

  db.query(sql, values, (error, data) => {
    if (error) {
      response.send(utils.createResult(error, null));
    } else {
      response.send(utils.createResult(null, data));
    }
  });
});
// from admin
router.get("/category/:id", (request, response) => {
  const sql = "SELECT * FROM category WHERE category_id=?"
  db.query(sql, [request.params.id], (error, data) => {
    response.send(utils.createResult(error, data))
  })
})

module.exports = router