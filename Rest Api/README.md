# Zee5-Api
As we all know, how famous is OTT platforms and also vulnerable😁. So, I get to know some zee5 flaws and thus here's the result... 
# How to use this API
Just make a get Request with the query param <b>q<b> and passing the URL of the Zee5 content e.g.
## baseUrl/search?q=https://www.zee5.com/movies/details/kaagaz/0-0-296370
<br>

# How this flaw works ??
So, zee5 has their own API used to get all relevant dataa about that stuff with id.
## https://gwapi.zee5.com/content/details/${id}?translation=en&country=IN&version=2
We need to pass id and access token while making request and the server will return us with useful data.
We will get our accesstoken from this url :-
## https://useraction.zee5.com/tokennd/
