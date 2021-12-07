const db = require('../db')
class PersonController {
    async createPerson(req, res) {
        const {firstName, lastName, telephoneNumber} = req.body
        const newPerson = await db.query(`INSERT INTO person (firstName, lastName, telephoneNumber) values ($1, $2, $3) RETURNING * `, [firstName, lastName, telephoneNumber])
        newPerson.rows.length > 0 ? res.json(newPerson.rows[0]) : res.status(400).json("Error");
    }
    async getPerson(req, res) {
        const id = req.params.id;
        const person = await db.query(`SELECT * FROM person where id = $1`, [id])
        person.rows.length > 0 ? res.json(person.rows[0]) : res.status(400).json("Person doesn't exist");
    }

    async getPersons(req, res) {
        const persons = await db.query(`SELECT * FROM person`)
        persons.rows.length > 0 ? res.json(persons.rows) : res.status(400).json("Persons doesn't exist");
    }

    async updatePerson(req, res) {
        const {id, firstName, lastName, telephoneNumber} = req.body;
        const person = await db.query(`UPDATE person set firstName = $1, lastName = $2, telephoneNumber = $3 
        where id = $4 RETURNING *`, [firstName, lastName, telephoneNumber, id])
        person.rows.length > 0 ? res.json(person.rows[0]) : res.status(400).json("Person doesn't exist");
    }

    async deletePerson(req, res) {
        const id = req.params.id;
        const person = await db.query(`UPDATE person set isDeleted = true where id = $1 AND isDeleted = false RETURNING *`, [id])
        person.rows.length > 0 ? res.json(person.rows[0]) : res.status(400).json("Person doesn't exist or already deleted");
    }
}

module.exports = new PersonController()