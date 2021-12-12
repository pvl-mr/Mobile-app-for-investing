const Router = require('express')
const router = new Router()
const passport = require('passport')
const portfolioController = require('../controllers/portfolio.controller')

router.post('/portfolio', passport.authenticate('jwt', {session: false}), portfolioController.createPortfolio)
router.get('/portfolio/:id', portfolioController.getPortfolio)
router.get('/portfolios', portfolioController.getPortfolios)
router.put('/portfolio', passport.authenticate('jwt', {session: false}), portfolioController.updatePortfolio)
router.delete('/portfolio/:id', passport.authenticate('jwt', {session: false}), portfolioController.deletePortfolio)

module.exports = router