const mysql = require("mysql")

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "manager",
  database: "veggies",
  multipleStatements: true
})

connection.connect()

module.exports = connection ;
