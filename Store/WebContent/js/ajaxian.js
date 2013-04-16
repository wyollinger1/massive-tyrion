require(['dojo/dom', 'dojo/query','dojo/on','dojo/request', 
    'dojo/domForm', 'dojo/domReady!'], 
    function(dom, on, query, request, domForm){
 /**
  * Setup ajax
  */
  var trConnections = [];
  /**
   * Connect searchForm's submit to ajax request and 
   * search table creation
   */
  on(dom.byId('searchForm'), "submit", function(evt){
    // prevent the page from navigating after submit
    evt.stopPropagation();
    evt.preventDefault();

    request('/Store/search', {
      data: domForm.toObject('searchFrom'),
      handleAs: "json",
      timeout: 2000
    }).then(function(data){
      var table=query("#searchTable table")[0];
      var newTableData=createTable(data);
      if(newTableData && table){
        table.innerHTML=newTableData;
      }else{
        //Error processing
      }
    });
  });
  //Sign in ajax
  on(dom.byId("logonForm"), "submit", function(evt){
    // prevent the page from navigating after submit
    evt.stopPropagation();
    evt.preventDefault();

    request.post('/Store/login', {
      data: domForm.toObject('logonForm'),
      handleAs: "json",
      timeout: 2000
    }).then(function(user){
      if(data && data['name']){
        alert("Hello "+ data['name']);
        userCustomize(user);
      }else{
        //Error processing TODO: maybe bootstraps alert?
      }
    });
  });
  

  //TODO: Connect up rest of Customer type buttons (purchase)
  //      also make rest of HTML generators (popView, userCustomize, logout)
  //      Then work on Manager stuff

  /**
   * Utility functions (HTML manipulation/creation mostly
   */

  /**
   * Creates a table from a array of objects (all of the same type
   *
   * @param lOfObjs the list of all same type object
   * @return String representation of the HTML <thead> and <tbody> (no <table>)
   */
  function createTable(lOfObjs){
    if(lOfObjs.length && lOfObjs.length>0){
      var tableHead = "<thead>\n\t<tr>";
      var tableBody = "<tbody>";
      var tr ="";
      //Setup header
      for(var i in lOfObj[0]){
        tableStr+="\n\t\t<th>"+i+"</th>";
      }
      tableHead+="\n\t</tr>\n</thead>";

      //Setup body
      for(i=0; i<lOfObj.length; i++){
        tr = "\n\t<tr>";
        for(j in lOfObj[i]){
          tr+="\n\t\t<td>"+lOfObj[i]+"</td>";
        }
        tr +="\n\t</tr>";
        tbody+=tr;
      }
      return thead+tbody;
    }
  }

  /**
   * Connect search trs on clicks to populate the view pane
   */
  function conTrs(table){
    //Remove past connections
    while(trConnections.length>0){
      trConnections.pop().remove();
    }

    //And make some new ones
    query('tr', table).forEach(function(node, index, nodeList){
      on(node, 'click', popView);
    });
  }

  /**
   * HTML generator for populating a view from a tr click
   */
  function popView(evt){
  }
  /**
   * Nav bar HTML customizer based on logged in user
   * Swaps login for logout button.
   */
  function userCustomize(user){
  }
});
