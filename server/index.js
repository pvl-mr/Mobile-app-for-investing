const express = require('express')
const passport = require('passport')
const stockRouter = require('./routes/stock.routes')
const bondRouter = require('./routes/bond.routes')
const portfolioRouter = require('./routes/portfolio.routes')
const authRouter = require('./routes/auth.routes')
const portfolioStockRouter = require('./routes/portfolioStock.routes')
const portfolioBondRouter = require('./routes/portfolioBond.routes')
require('dotenv').config()

const PORT = process.env.PORT || 5000;

const app = express()

app.use(passport.initialize())
require('./passport')(passport)

app.use(express.json())
app.use('/', stockRouter)
app.use('/', bondRouter)
app.use('/', authRouter)
app.use('/', portfolioRouter)
app.use('/', portfolioStockRouter)
app.use('/', portfolioBondRouter)

app.listen(PORT, () => console.log('server has been started'))


