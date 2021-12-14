const db = require('../db')
class PortfolioStockController {
    async addStockToPortfolio(req, res) {
        const {count, stock_id, portfolio_id} = req.body
        let newPortfolioStock = await db.query(`SELECT * FROM portfolio_stock where (stockid = $1 and portfolioid = $2)`, [stock_id, portfolio_id])
        let isNew = newPortfolioStock.rows.length > 0 ? 0 : 1
        if (isNew) {
            newPortfolioStock = await db.query(`INSERT INTO portfolio_stock (count, stockId, portfolioId) values ($1, $2, $3) RETURNING * `, [count, stock_id, portfolio_id])
        } else {
            let countBefore = await (await db.query(`SELECT count FROM portfolio_stock WHERE (stockid = $1 and portfolioid = $2)`, [stock_id, portfolio_id])).rows[0].count
            let newCount = count + countBefore
            newPortfolioStock = await db.query(`UPDATE portfolio_stock set count = $1 WHERE (stockid = $2 and portfolioid = $3) RETURNING *`, [newCount, stock_id, portfolio_id])
        }
        
        newPortfolioStock.rows.length > 0 ? res.json(newPortfolioStock.rows[0]) : res.status(400).json("Error");
    }

    async getPortfolioStocks(req, res) {
        const id = req.params.id;     
        const portfolioStock = await db.query(`SELECT stockname, price, count, stockdesc
                                               from portfolio_stock, stock
                                               where (portfolio_stock.portfolioid = $1
                                               and portfolio_stock.stockid = stock.id)
                                              `, [id])
        portfolioStock.rows.length > 0 ? res.status(200).json({
            status: 'ok',
            data: portfolioStock.rows
        }) : res.status(400).json("Stocks doesn't exist");
    }

    async getPortfolioStock(req, res) {
        const id = req.params.id; 
        const portfolioStocks = await db.query(`SELECT stockname, count, stockdesc
                                                from portfolio_stock, stock
                                                where (portfolio_stock.id = $1
                                                and portfolio_stock.stockid = stock.id)`, [id])
        portfolioStocks.rows.length > 0 ? res.json(portfolioStocks.rows) : res.status(400).json("PortfolioStocks doesn't exist");
    }

    async updatePortfolioStock(req, res) {
        const {id, count} = req.body;
        const portfolioStock = await db.query(`UPDATE portfolio_stock set count = $1 where id = $2 RETURNING *`, [count, id])
        portfolioStock.rows.length > 0 ? res.json(portfolioStock.rows[0]) : res.status(400).json("PortfolioStock doesn't exist");
    }

    async deleteStockFromPortfolio(req, res) {
        const id = req.params.id;
        const portfolioStock = await db.query(`DELETE from portfolio_stock where id = $1 RETURNING *`, [id])
        portfolioStock.rows.length > 0 ? res.json(portfolioStock.rows[0]) : res.status(400).json("PortfolioStock doesn't exist or already deleted");
    }
}

module.exports = new PortfolioStockController()