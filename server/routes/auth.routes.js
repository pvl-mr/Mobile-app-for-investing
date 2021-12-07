const Router = require('express')
const router = new Router()
const authController = require('../controllers/auth.controller.js')

router.post('/register', authController.register)
router.post('/login', authController.login)

module.exports = router