define(['dojo/dom', 'dojo/query','dojo/on','dojo/request', 
    'dojo/dom-form', 'dojo/dom-class', 'dojo/domReady!'], 
    function(dom, query, on, request, domForm, domClass){
      /**
       * Nav bar HTML customizer based on logged in user
       * Swaps login for logout button.
       */
      return {'userCustomize':function(user){
        dom.byId('dispUsername').innerHTML=user['name'];
        dom.byId('dispBalance').innerHTML="$"+user['balance'];
        domClass.add('logonForm', "hide");
        domClass.remove('loggedIn', "hide");
        console.log(user);
        console.log(user.isManager);
        if(user.isManager){
          domClass.remove('manBut', "hide");
        }
      },
      'logout':function(){
        dom.byId('dispUsername').innerHTML="";
        dom.byId('dispBalance').innerHTML="";
        dom.byId('logonForm').reset();
        domClass.remove('logonForm', "hide");
        domClass.add('loggedIn', "hide");
        domClass.add('manBut', "hide");
      }};
    });
