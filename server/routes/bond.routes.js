const Router = require('express')
const router = new Router()
const passport = require('passport')
const bondController = require('../controllers/bond.controller')

router.post('/bond', bondController.createBond)
router.get('/bond/:id', bondController.getBond)
router.get('/bond', bondController.getBonds)
router.put('/bond', bondController.updateBond)
router.delete('/bond/:id', bondController.deleteBond)

module.exports = router