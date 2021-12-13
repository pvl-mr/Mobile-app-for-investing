const Router = require('express')
const router = new Router()
const passport = require('passport')
const portfolioController = require('../controllers/portfolio.controller')

router.post('/portfolio', portfolioController.createPortfolio)
router.get('/portfolio/:id', portfolioController.getPortfolio)
router.get('/portfolios', portfolioController.getPortfolios)
router.put('/portfolio', portfolioController.updatePortfolio)
router.delete('/portfolio/:id', portfolioController.deletePortfolio)

module.exports = router