const db = require('../db')
class PortfolioController {
    async createPortfolio(req, res) {
        const {years, goal, client_id, analyst_id} = req.body
        const newPortfolio = await db.query(`INSERT INTO portfolio (years, goal, clientid, analystid) values ($1, $2, $3, $4) RETURNING * `, [years, goal, client_id, analyst_id])
        newPortfolio.rows.length > 0 ? res.json(newPortfolio.rows[0]) : res.status(400).json("Error");
    }
    async getPortfolio(req, res) {
        const id = req.params.id;
        const portfolio = await db.query(`SELECT * FROM portfolio where id = $1`, [id])
        portfolio.rows.length > 0 ? res.json(portfolio.rows[0]) : res.status(400).json("Portfolio doesn't exist");
    }

    async getPortfolios(req, res) {
        const portfolios = await db.query(`SELECT * FROM portfolio`)
        portfolios.rows.length > 0 ? res.json(portfolios.rows) : res.status(400).json("Portfolios doesn't exist");
    }

    async updatePortfolio(req, res) {
        const {id, years, goal, analyst_id} = req.body;
        const portfolio = await db.query(`UPDATE portfolio set years = $1, goal = $2, analystid = $3 where id = $4 RETURNING *`, [years, goal, analyst_id, id])
        portfolio.rows.length > 0 ? res.json(portfolio.rows[0]) : res.status(400).json("Portfolio doesn't exist");
    }

    async deletePortfolio(req, res) {
        const id = req.params.id;
        const portfolio = await db.query(`DELETE from portfolio where id = $1 RETURNING *`, [id])
        portfolio.rows.length > 0 ? res.json(portfolio.rows[0]) : res.status(400).json("Portfolio doesn't exist or already deleted");
    }
}

module.exports = new PortfolioController()