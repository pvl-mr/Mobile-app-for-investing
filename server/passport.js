const JwtStrategy = require('passport-jwt').Strategy
const ExtractJwt = require('passport-jwt').ExtractJwt
const db = require('./db')
const config = require('./config')

const options = {
    jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
    secretOrKey: config.jwtKey
}
module.exports = passport => {
    passport.use(
        new JwtStrategy(options, async (payload, done) => {
            try {
                let user = await db.query(`SELECT * FROM CLIENT where id = $1`, [payload.userId])
                if (user.rowCount == 0) user = await db.query(`SELECT * FROM ANALYST where id = $1`, [payload.userId])
                if (user.rowCount > 0) {
                    done(null, user.rows[0])
                } else {
                    done(null, false)
                }
            } catch (e) {
                console.log(e)
            }
        })
    )
}

