const db = require('../db')
class PortfolioController {
    async createPortfolio(req, res) {
        const {years, goal, client_id} = req.body
        console.log('reg.body@@@@@@@@@', req.body);
        const getRandomIntInclusive = (min, max) => {
            min = Math.ceil(min);
            max = Math.floor(max);
            return Math.floor(Math.random() * (max - min + 1)) + min; //Максимум и минимум включаются
          }
        let analystid = await db.query(`select id from analyst`)
        console.log(analystid)
        let size = analystid.rows.length - 1
        let i = getRandomIntInclusive(0, size)
        let analyst_id = analystid.rows[i].id
        console.log(analyst_id)

        const newPortfolio = await db.query(`INSERT INTO portfolio (years, goal, clientid, analystid) values ($1, $2, $3, $4) RETURNING * `, [years, goal, client_id, analyst_id])

        newPortfolio.rows.length > 0 ? res.status(201).json(newPortfolio.rows[0]) : res.status(400).json("Error");
    }
    async getPortfolio(req, res) {
        const id = req.params.id;
        const portfolio = await db.query(`SELECT * FROM portfolio where clientid = $1`, [id])
        portfolio.rows.length > 0 ? res.status(200).json({
            status: 'ok',
            data: portfolio.rows
        }) : res.status(400).json("Portfolios doesn't exist");
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