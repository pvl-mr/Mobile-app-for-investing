const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')
const db = require('../db')
const config = require('../config')

class AuthController {
    async login(req, res) {
        const {email, password} = req.body
        const candidate = await db.query('SELECT * FROM USERS WHERE email = $1', [email])
        if (candidate.rowCount > 0) {
            const isMatch = bcrypt.compareSync(password, candidate.rows[0].password)
                if (isMatch) {
                    const token = jwt.sign({
                        email: candidate.rows[0].email,
                        userId: candidate.rows[0].id
                    }, config.jwtKey, {expiresIn: config.ttl})
                    res.status(200).json({
                        status: "ok",
                        body: token
                    })
                } else {
                    res.status(401).json({
                        message: "Пароли не совпадают."
                    })
                }
        } else {
            res.status(404).json({
                message: "Пользователя с таким email не существует."
            })
        }
    }

    async register(req, res) {
        const {email, password} = req.body
        const candidate = await db.query('SELECT * FROM USERS WHERE email = $1', [email])
        console.log()
        if (candidate.rowCount > 0) {
            res.status(409).json({
                message: 'Пользователь с таким email уже зарегистрирован'
            })
        } else {
            const salt = bcrypt.genSaltSync(10)
            const crPassword = bcrypt.hashSync(password, salt)
            const newUser = await db.query(`INSERT INTO users (email, password) values ($1, $2) RETURNING * `, [email, crPassword])
            newUser.rows.length > 0 ? res.status(201).json({status: "ok"}) : res.status(400).json("Error");
        }
    }
}

module.exports = new AuthController()
