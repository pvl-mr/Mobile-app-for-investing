const Router = require('express')
const router = new Router()
const passport = require('passport')
const bondController = require('../controllers/portfolioBond.controller')

router.post('/portfolioBond', bondController.addBondToPortfolio)
router.get('/portfolioBonds/:id', bondController.getPortfolioBonds)
router.get('/portfolioBond/:id', bondController.getPortfolioBond)
router.put('/portfolioBond', bondController.updatePortfolioBond)
router.delete('/portfolioBond/:id', bondController.deleteBondFromPortfolio)

module.exports = router
