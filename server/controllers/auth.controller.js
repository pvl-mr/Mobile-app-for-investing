const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')
const db = require('../db')
const config = require('../config')

class AuthController {
    async login(req, res) {
        const {email, pass, } = req.body
        let candidate = await db.query('SELECT * FROM CLIENT WHERE email = $1', [email])
        if (candidate.rowCount == 0) candidate = await db.query('SELECT * FROM ANALYST WHERE email = $1', [email])
        if (candidate.rowCount > 0) {
            const isMatch = bcrypt.compareSync(pass, candidate.rows[0].pass)
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
        const {firstName, lastName, email, pass, code} = req.body
        let candidate = await db.query('SELECT * FROM CLIENT WHERE email = $1', [email])
        if (candidate.rowCount == 0) candidate = await db.query('SELECT * FROM ANALYST WHERE email = $1', [email])
        console.log()
        if (candidate.rowCount > 0) {
            res.status(409).json({
                message: 'Пользователь с таким email уже зарегистрирован'
            })
        } else {
            const salt = bcrypt.genSaltSync(10)
            const crPassword = bcrypt.hashSync(pass, salt)
            let newUser;
            if (code != null) {
                newUser = await db.query(`INSERT INTO ANALYST (firstName, lastName, email, pass, code) values ($1, $2, $3, $4, $5) RETURNING * `, [firstName, lastName, email, crPassword, code])
            } else {
                newUser = await db.query(`INSERT INTO CLIENT (firstName, lastName, email, pass) values ($1, $2, $3, $4) RETURNING * `, [firstName, lastName, email, crPassword])
            }   
           
            newUser.rows.length > 0 ? res.status(201).json({status: "ok"}) : res.status(400).json("Error");
        }
    }
}

module.exports = new AuthController()
