const db = require('../db')
class PetController {
    async createPet(req, res) {
        const {name, breed, age, personId} = req.body;
        const newPet = await db.query(`INSERT INTO pet (name, breed, age, personId) values ($1, $2, $3, $4) RETURNING *` , [name, breed, age, personId])
        newPet.rows.length > 0 ? res.json(newPet.rows[0]) : res.status(400).json("Error");
    }

    async getPetsByPerson(req, res) {
        const id = req.query.id
        const pets = await db.query('SELECT * FROM pet where personId = $1', [id])
        pets.rows.length > 0 ? res.json(pets.rows) : res.status(400).json("Pets doesn't exist");
    }

    async getPet(req, res) {
        const id = req.params.id;
        const pet = await db.query(`SELECT * FROM pet where id = $1`, [id])
        pet.rows.length > 0 ? res.json(pet.rows[0]) : res.status(400).json("Pet doesn't exist");
    }

    async updatePet(req, res) {
        const {id, name, breed, age, personId} = req.body;
        const pet = await db.query(`UPDATE pet set name = $1, breed = $2, age = $3, personId = $4
        where id = $5 RETURNING *`, [name, breed, age, personId, id])
        pet.rows.length > 0 ? res.json(pet.rows[0]) : res.status(400).json("Pet doesn't exist");
    }

    async deletePet(req, res) {
        const id = req.params.id;
        const pet = await db.query(`UPDATE pet set isDeleted = true where id = $1 RETURNING *`, [id])
        pet.rows.length > 0 ? res.json(pet.rows[0]) : res.status(400).json("Pet doesn't exist or already deleted");
    }
}

module.exports = new PetController()