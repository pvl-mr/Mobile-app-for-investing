const db = require('../db')
class StockController {
    async createStock(req, res) {
        const {count, stockName} = req.body
        const newStock = await db.query(`INSERT INTO stock (count, stockName) values ($1, $2) RETURNING * `, [count, stockName])
        newStock.rows.length > 0 ? res.json(newStock.rows[0]) : res.status(400).json("Error");
    }
    async getStock(req, res) {
        const id = req.params.id;
        const stock = await db.query(`SELECT * FROM stock where id = $1`, [id])
        stock.rows.length > 0 ? res.json(stock.rows[0]) : res.status(400).json("Stock doesn't exist");
    }

    async getStocks(req, res) {
        const stocks = await db.query(`SELECT * FROM stock`)
        stocks.rows.length > 0 ? res.json(stocks.rows) : res.status(400).json("Stocks doesn't exist");
    }

    async updateStock(req, res) {
        const {id, count, stockName} = req.body;
        const stock = await db.query(`UPDATE stock set count = $1, stockName = $2 where id = $3 RETURNING *`, [count, stockName, id])
        stock.rows.length > 0 ? res.json(stock.rows[0]) : res.status(400).json("Stock doesn't exist");
    }

    async deleteStock(req, res) {
        const id = req.params.id;
        const stock = await db.query(`DELETE stock set where id = $1 RETURNING *`, [id])
        stock.rows.length > 0 ? res.json(stock.rows[0]) : res.status(400).json("Stock doesn't exist or already deleted");
    }
}

module.exports = new StockController()