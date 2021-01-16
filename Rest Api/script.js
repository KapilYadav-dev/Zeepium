const fetch = require('node-fetch');
async function handleRequest(query,res) {
    let req_query = query
    if (req_query == null) {

        const reply = {
            "status": "failed",
            "message": "Query is empty !!!"
        };

        res.status(404).send(reply)

    } else {
        var id = req_query.split("/").pop()
    }

    var result = await fetch(`https://gwapi.zee5.com/content/details/${id}?translation=en&country=IN&version=2`, {
        headers: {
            "x-access-token": await token(),
            'Content-Type': 'application/json'
        }
    })
    var result = await result.json()

    const error_msg = {
        "status": "failed",
        "message": "Invalid URL or content not scrappable"
    }

    if (result.title == undefined || result.image_url == undefined) {
        console.log(result);
        res.status(404).send(error_msg)
    } else {
        var pass = ({
            title: result.title,
            image: result.image_url,
            description: result.description,
            hls: `https://zee5vodnd.akamaized.net${result.hls[0].replace("drm", "hls")}${await token()}`
        })
        res_data = {
            "id":id,
            "title": pass.title,
            "description": pass.description,
            "img": pass.image,
            "url": pass.hls
        }
        res.status(200).send(res_data)
        pass=[],res_data=[]
    }

    async function token() {
        var token = await fetch('https://useraction.zee5.com/tokennd/')
        var token = await token.json()
        console.log(token);
        return token.video_token
    }
}
module.exports.handleRequest=handleRequest