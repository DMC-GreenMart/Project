const multer = require('multer')
const express = require('express') 
const db = require('./db')
const app = express()
const upload = multer({dest: 'images'})
;
app.use(express.json())
app.use(express.static('images'))
// app.use('/images', express.static('images')); /

app.get("/", (request , response) => {
    // res.send("hii")
 let sql = "select * from category ;"
    db.execute(sql, (error , result) =>{
        if(error == null)
        {
            // response.send(result, myimg+"/"+result.category_image)  (


            response.send(result)
        } 
        else{
         response.send(error)
        }
 })

})
app.post('/' , upload.single('image') , 
(request ,response) => {

    let { name } = request.body
    let sql= `insert into category ( category_name , category_image ) values (? , ?)`
    

    db.execute(sql ,[ name  ,  request.file.filename] , (error , result) =>{
           if(error == null)
           {
               response.send(result)  
           } 
           else{
            response.send(error)
           }
    })

})
app.listen( 7898, ()=>{
    console.log("server started on port 7899")
})