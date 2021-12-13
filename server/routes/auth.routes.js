const Router = require('express')
const router = new Router()
const authController = require('../controllers/auth.controller.js')

router.post('/register', authController.register)
router.post('/login', authController.login)
router.get('/client/:id', authController.getPersonClient)

module.exports = router