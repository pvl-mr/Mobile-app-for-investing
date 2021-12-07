const Router = require('express')
const router = new Router()
const petController = require('../controllers/pet.controller')
const passport = require('passport')

router.post('/pet', passport.authenticate('jwt', { session: false }), petController.createPet)
router.get('/pet/:id', petController.getPet)
router.get('/pet', petController.getPetsByPerson)
router.put('/pet', passport.authenticate('jwt', {session: false}), petController.updatePet)
router.delete('/pet/:id', passport.authenticate('jwt', {session: false}), petController.deletePet)

module.exports = router