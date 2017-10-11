const mongoose = require('mongoose')

const pushSchema = mongoose.Schema({
    article_id: { type: String, required: true },
    article_title: { type: String, required: true },
    author: { type: String, required: true },
    link: { type: String },
    content: { type: String },
    sources: { type: String },
    classification: { type: String },
    user_id: { type: mongoose.Schema.Types.ObjectId, required: true }
})

const Push = module.exports = mongoose.model('Push', pushSchema)
