var express = require('express');
var router = express.Router();
const User = require('../models/user');
var crypto = require('crypto');

const STATUS_ERR = 0;
const STATUS_OK = 1;


//登录检查
router.post('/login', function(req, res, next) {
    User.findOne({ 'email': req.body.email }, function(err, user) {
        if(err) return handleError(err);
        if(!user) {
            res.json({
                'status': STATUS_ERR,
                'message': '该用户不存在！'
            })
        } else if(user.password != cryptPwd(req.body.password)) {
            res.json({
                'status': STATUS_ERR,
                'message': '密码错误，请重新输入！'
            })
        } else {
            res.json({
                'status': STATUS_OK,
                'message': '合法用户！',
                data: user
            })
        }
    });
});

/* GET users listing. */
router.get('/findAllUsers', function(req, res, next) {
    User.find({})
        .sort({ update_at: -1 })
        .then(users => {
            res.json(users)
        })
        .catch(err => {
            res.json(err)
        });
});
//根据id获取用户信息
router.get('/getUser/:id', (req, res) => {
    User.findById(req.params.id)
        .then(user => {
            res.json(user)
        })
        .catch(err => {
            res.json(err)
        });
});
//添加用户
router.post('/addUser', (req, res) => {
    var user = req.body;
    user.password = cryptPwd(user.password);

    User.findOne({ 'email': user.email }, function(err, user) {
        if(err) return handleError(err);
        if(!user) {
            User.create(req.body, (err, user) => {
                if(err) {
                    res.json({
                        'status': STATUS_ERR,
                        'message': '注册失败，请稍后重试！'
                    })
                } else {
                    res.json({
                        'status': STATUS_OK,
                        'message': '注册成功！',
                        data: user
                    })
                }
            });
        } else {
            res.json({
                'status': STATUS_ERR,
                'message': '该邮箱已经被注册！'
            })
        }
    });
});
//更新用户信息
router.put('/updateUser/:id', (req, res) => {
    User.findOneAndUpdate({ _id: req.params.id }, {
            $set: {
                nickname: req.body.nickname
            }
        }, {
            new: true
        })
        .then(user => res.json(user))
        .catch(err => res.json(err));
});
//删除用户
router.delete('/deleteUser/:id', (req, res) => {
    User.findOneAndRemove({
            _id: req.params.id
        })
        .then(movie => res.send(`${user.nickname}删除成功`))
        .catch(err => res.json(err));
})

//密码md5加密
function cryptPwd(password) {
    var md5 = crypto.createHash('md5');
    return md5.update(password).digest('hex');
}


module.exports = router;
