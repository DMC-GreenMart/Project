const express = require("express")
const cors = require("cors")
const app = express()

const userRouter = require("./router/user")
const categoryRouter = require("./router/category")
const productRouter = require("./router/prodcuts")
const cartRouter = require("./router/cart")
const addressRouter = require("./router/address");
const placeorderRouter = require("./router/placeorder");
const orderRouter = require("./router/order");

// const movieRouter = require("./router/movie")
// const shareRouter = require("./router/share")

app.use(cors("*"))
app.use(express.json())

app.use(express.static("uploads"))

app.use("/user", userRouter)
app.use("/category", categoryRouter)
app.use("/product", productRouter)
app.use("/cart",cartRouter)
app.use("/address",addressRouter)
app.use("/placeorder", placeorderRouter)
app.use("/order", orderRouter)

// app.use("/movie", movieRouter)
// app.use("/share", shareRouter)

// sendMail("dd" , 4565);



app.listen(9898, "0.0.0.0", () => {
  console.log("Server started on port 9898")
})
