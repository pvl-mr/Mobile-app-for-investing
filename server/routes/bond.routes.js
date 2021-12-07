const Router = require('express')
const router = new Router()
const passport = require('passport')
const bondController = require('../controllers/bond.controller')

router.post('/bond', passport.authenticate('jwt', {session: false}), bondController.createBond)
router.get('/bond/:id', bondController.getBond)
router.get('/bond', bondController.getBonds)
router.put('/bond', passport.authenticate('jwt', {session: false}), bondController.updateBond)
router.delete('/bond/:id', passport.authenticate('jwt', {session: false}), bondController.deleteBond)

module.exports = router