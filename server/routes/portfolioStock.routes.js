const Router = require('express')
const router = new Router()
const passport = require('passport')
const stockController = require('../controllers/portfolioStock.controller')

router.post('/portfolioStock', stockController.addStockToPortfolio)
router.get('/portfolioStocks/:id', stockController.getPortfolioStocks)
router.get('/portfolioStock/:id', stockController.getPortfolioStock)
router.put('/portfolioStock', stockController.updatePortfolioStock)
router.delete('/portfolioStock/:id', passport.authenticate('jwt', {session: false}), stockController.deleteStockFromPortfolio)

module.exports = router
