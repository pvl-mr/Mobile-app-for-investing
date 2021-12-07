const express = require('express')
const passport = require('passport')
const stockRouter = require('./routes/stock.routes')
const bondRouter = require('./routes/bond.routes')
const petRouter = require('./routes/pet.routes')
const authRouter = require('./routes/auth.routes')
require('dotenv').config()

const PORT = process.env.PORT || 5000;

const app = express()

app.use(passport.initialize())
require('./passport')(passport)

app.use(express.json())
app.use('/', stockRouter)
app.use('/', bondRouter)
app.use('/', petRouter)
app.use('/', authRouter)

app.listen(PORT, () => console.log('server has been started'))


