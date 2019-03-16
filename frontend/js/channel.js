const channelList = {"ok":true,"channels":[{"id":"CGMFSKE90","name":"general","is_channel":true,"created":1551551394,"is_archived":false,"is_general":true,"unlinked":0,"creator":"UGMRC9K1B","name_normalized":"general","is_shared":false,"is_org_shared":false,"is_member":true,"is_private":false,"is_mpim":false,"members":["UGMRC9K1B","UGXEC6K0F","UGYFF10PM"],"topic":{"value":"Company-wide announcements and work-based matters","creator":"UGMRC9K1B","last_set":1551551394},"purpose":{"value":"This channel is for workspace-wide communication and announcements. All members are in this channel.","creator":"UGMRC9K1B","last_set":1551551394},"previous_names":[],"num_members":3},{"id":"CGMFSKG82","name":"pixel","is_channel":true,"created":1551551394,"is_archived":false,"is_general":false,"unlinked":0,"creator":"UGMRC9K1B","name_normalized":"pixel","is_shared":false,"is_org_shared":false,"is_member":true,"is_private":false,"is_mpim":false,"members":["UGMRC9K1B","UGXEC6K0F","UGYFF10PM"],"topic":{"value":"","creator":"","last_set":0},"purpose":{"value":"","creator":"","last_set":0},"previous_names":[],"num_members":3},{"id":"CGNUHFBGW","name":"random","is_channel":true,"created":1551551394,"is_archived":false,"is_general":false,"unlinked":0,"creator":"UGMRC9K1B","name_normalized":"random","is_shared":false,"is_org_shared":false,"is_member":true,"is_private":false,"is_mpim":false,"members":["UGMRC9K1B","UGXEC6K0F","UGYFF10PM"],"topic":{"value":"Non-work banter and water cooler conversation","creator":"UGMRC9K1B","last_set":1551551394},"purpose":{"value":"A place for non-work-related flimflam, faffing, hodge-podge or jibber-jabber you'd prefer to keep out of more focused work-related channels.","creator":"UGMRC9K1B","last_set":1551551394},"previous_names":[],"num_members":3}],"response_metadata":{"next_cursor":""}};

function getChannelNames(channelList) {
    const channels = channelList.channels;

    const channelNames = channels.reduce((accum, currentValue) => {
         accum.push(currentValue.name);

         return accum;
    }, []);

    return channelNames;
}

function getChannelListFromApi() {
    fetch('http://localhost:8080/api/channelList', {mode: 'no-cors'})
        .then(
            function(response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }

                // Examine the text in the response
                response.json().then(function(data) {
                    const channelNames = getChannelNames(data);
                    console.log(channelNames);
                    return channelNames;
                });
            }
        )
        .catch(function(err) {
            console.log('Fetch Error :-S', err);
        });
}

// document.getElementById("channel-list").innerHTML = getChannelNames(channelList);
document.getElementById("channel-list").innerHTML = getChannelListFromApi();