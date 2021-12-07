const db = require('../db')
class BondController {
    async createBond(req, res) {
        const {count, bondName} = req.body
        const newBond = await db.query(`INSERT INTO bond (count, bondName) values ($1, $2) RETURNING * `, [count, bondName])
        newBond.rows.length > 0 ? res.json(newBond.rows[0]) : res.status(400).json("Error");
    }
    async getBond(req, res) {
        const id = req.params.id;
        const bond = await db.query(`SELECT * FROM bond where id = $1`, [id])
        bond.rows.length > 0 ? res.json(bond.rows[0]) : res.status(400).json("Bond doesn't exist");
    }

    async getBonds(req, res) {
        const bonds = await db.query(`SELECT * FROM bond`)
        bonds.rows.length > 0 ? res.json(bonds.rows) : res.status(400).json("Bonds doesn't exist");
    }

    async updateBond(req, res) {
        const {id, count, bondName} = req.body;
        const bond = await db.query(`UPDATE bond set count = $1, bondName = $2 where id = $3 RETURNING *`, [count, bondName, id])
        bond.rows.length > 0 ? res.json(bond.rows[0]) : res.status(400).json("Bond doesn't exist");
    }

    async deleteBond(req, res) {
        const id = req.params.id;
        const bond = await db.query(`DELETE bond set where id = $1 RETURNING *`, [id])
        bond.rows.length > 0 ? res.json(bond.rows[0]) : res.status(400).json("Bond doesn't exist or already deleted");
    }
}

module.exports = new BondController()