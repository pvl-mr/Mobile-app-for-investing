const db = require('../db')
class PortfolioBondController {
    async addBondToPortfolio(req, res) {
        console.log('-----------------')
        const {count, bond_id, portfolio_id} = req.body
        console.log("bond_id", req.body.bond_id)
        console.log("req.body", req.body)
        let newPortfolioBond = await db.query(`SELECT * FROM portfolio_bond where (bondid = $1 and portfolioid = $2)`, [bond_id, portfolio_id])
        let isNew = newPortfolioBond.rows.length > 0 ? 0 : 1
        if (isNew) {
            newPortfolioBond = await db.query(`INSERT INTO portfolio_bond (count, bondId, portfolioId) values ($1, $2, $3) RETURNING * `, [count, bond_id, portfolio_id])
        } else {
            let countBefore = await (await db.query(`SELECT count FROM portfolio_bond WHERE (bondid = $1 and portfolioid = $2)`, [bond_id, portfolio_id])).rows[0].count
            let newCount = count + countBefore
            newPortfolioBond = await db.query(`UPDATE portfolio_bond set count = $1 WHERE (bondid = $2 and portfolioid = $3) RETURNING *`, [newCount, bond_id, portfolio_id])
        }
        
        newPortfolioBond.rows.length > 0 ? res.json(newPortfolioBond.rows[0]) : res.status(400).json("Error");
    }

    async getPortfolioBonds(req, res) {
        const id = req.params.id;     
        const portfolioBond = await db.query(`SELECT bondname, count, bonddesc, price
                                               from portfolio_bond, bond
                                               where (portfolio_bond.portfolioid = $1
                                               and portfolio_bond.bondid = bond.id)
                                              `, [id])
        portfolioBond.rows.length > 0 ? res.status(200).json({
        status: 'ok',
        data: portfolioBond.rows
    }) : res.status(400).json("Bonds doesn't exist");
    }

    async getPortfolioBond(req, res) {
        const id = req.params.id; 
        const portfolioBonds = await db.query(`SELECT bondname, count, bonddesc
                                                from portfolio_bond, bond
                                                where (portfolio_bond.id = $1
                                                and portfolio_bond.bondid = bond.id)`, [id])
        portfolioBonds.rows.length > 0 ? res.json(portfolioBonds.rows) : res.status(400).json("PortfolioBonds doesn't exist");
    }

    async updatePortfolioBond(req, res) {
        const {id, count} = req.body;
        const portfolioBond = await db.query(`UPDATE portfolio_bond set count = $1 where id = $2 RETURNING *`, [count, id])
        portfolioBond.rows.length > 0 ? res.json(portfolioBond.rows[0]) : res.status(400).json("PortfolioBond doesn't exist");
    }

    async deleteBondFromPortfolio(req, res) {
        const id = req.params.id;
        const portfolioBond = await db.query(`DELETE from portfolio_bond where id = $1 RETURNING *`, [id])
        portfolioBond.rows.length > 0 ? res.json(portfolioBond.rows[0]) : res.status(400).json("PortfolioBond doesn't exist or already deleted");
    }
}

module.exports = new PortfolioBondController()