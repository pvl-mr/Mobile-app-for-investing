const Router = require('express')
const router = new Router()
const passport = require('passport')
const stockController = require('../controllers/stock.controller')

router.post('/stock', stockController.createStock)
router.get('/stock/:id', stockController.getStock)
router.get('/stock', stockController.getStocks)
router.put('/stock', stockController.updateStock)
router.delete('/stock/:id', stockController.deleteStock)

module.exports = router