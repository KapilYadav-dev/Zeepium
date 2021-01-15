document.addEventListener('DOMContentLoaded', () => {
  chrome.tabs.query({
      currentWindow: true,    // currently focused window
      active: true            // selected tab
    }, function (foundTabs) {
      if (foundTabs.length > 0) {
        var url = foundTabs[0].url
        var title=foundTabs[0].title
        if(url!='' && url.includes('https://www.zee5.com') && url.includes('details'))
        { // <--- this is what you are looking for
          var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: JSON.stringify({from:'Zeepium',title:title,url:url}),
            width: 256,
            height: 256,
            colorDark : "#000000",
            colorLight : "#ffffff",
            correctLevel : QRCode.CorrectLevel.H
          })  
        }
        else document.getElementById('text').innerHTML="I only work for Zeepium app or this is not streamable content...😄"
      }
    }
  )
})
