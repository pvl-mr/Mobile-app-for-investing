const Router = require('express')
const router = new Router()
const passport = require('passport')
const personController = require('../controllers/person.controller')

router.post('/person', passport.authenticate('jwt', {session: false}), personController.createPerson)
router.get('/person/:id', personController.getPerson)
router.get('/person', personController.getPersons)
router.put('/person', passport.authenticate('jwt', {session: false}), personController.updatePerson)
router.delete('/person/:id', passport.authenticate('jwt', {session: false}), personController.deletePerson)

module.exports = router