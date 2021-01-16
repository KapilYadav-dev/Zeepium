const express=require('express')
const app=express()
var port=process.env.PORT||8080
const script = require('./script');
const rateLimit = require("express-rate-limit");

const limiter = rateLimit({
    windowMs:  2000,
    max: 1,
    message: {
        status:"Error",
        message:"We are hosted on free server, please reduce calls."
    }
  })

app.get('/search',limiter,(req,res)=>{
    script.handleRequest(req.query.q,res)
})

app.listen(port,()=>{
    console.log("Server running on port "+port)
})

