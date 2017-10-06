const mongoose = require('mongoose')

const userSchema = mongoose.Schema({
    email: { type: String, required: true },
    nickname: { type: String, required: true },
    password: { type: String, required: true },
    sign_up_date: { type: Date, default: Date.now }
})

const User = module.exports = mongoose.model('User', userSchema)
