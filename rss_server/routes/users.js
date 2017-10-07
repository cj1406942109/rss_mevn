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
                'data': user
            })
        }
    });
});

/* GET users listing. */
router.post('/findAllUsers', function(req, res, next) {
    User.find({})
        .sort({ sign_up_date: -1 })
        .then(users => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': users
            })
        })
        .catch(err => {
            res.json({
                'status': STATUS_ERR,
                'message': '获取数据失败，请稍后重试！',
                'data': err
            })
        });
});
//根据id获取用户信息
router.post('/getUser', (req, res) => {
    User.findById(req.params.id)
        .then(user => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': user
            })
        })
        .catch(err => {
            res.json({
                'status': STATUS_ERR,
                'message': '获取数据失败，请稍后重试！',
                'data': err
            })
        });
});
//添加用户
router.post('/addUser', (req, res) => {
    var new_user = req.body;
    new_user.password = cryptPwd(new_user.password);

    User.findOne({ 'email': new_user.email }, function(err, user) {
        if(err) return handleError(err);
        if(!user) {
            User.create(new_user, (err, new_user) => {
                if(err) {
                    res.json({
                        'status': STATUS_ERR,
                        'message': '注册失败，请稍后重试！',
                        'data': err
                    })
                } else {
                    res.json({
                        'status': STATUS_OK,
                        'message': '注册成功！',
                        'data': new_user
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
router.post('/updateUser', (req, res) => {
    User.findOneAndUpdate({ _id: req.body.id }, {
            $set: {
                nickname: req.body.nickname
            }
        }, {
            new: true //返回更新后的文档，默认为更新前的文档
        })
        .then(user => res.json({
            'status': STATUS_OK,
            'message': '更新成功！',
            'data': user
        }))
        .catch(err => res.json({
            'status': STATUS_ERR,
            'message': '更新失败，请稍微重试！',
            'data': err
        }));
});
//删除用户
router.post('/deleteUser', (req, res) => {
    User.findOneAndRemove({
            _id: req.body.id
        })
        .then(user => res.json({
            'status': STATUS_OK,
            'message': `${user.nickname}删除成功！`,
            'data': review
        }))
        .catch(err => res.json({
            'status': STATUS_ERR,
            'message': '删除失败，请稍微重试！',
            'data': err
        }));
})

//密码md5加密
function cryptPwd(password) {
    var md5 = crypto.createHash('md5');
    return md5.update(password).digest('hex');
}


module.exports = router;
