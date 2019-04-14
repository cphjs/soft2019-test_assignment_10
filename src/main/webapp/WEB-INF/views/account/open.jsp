<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:body>
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <h3 class="card-header">Open an account</h3>
                    <div class="card-body">
                        <c:if test="fields != null">
                            <div class="alert alert-danger">
                               ${fields}
                            </div>
                        </c:if>
                        <form action="/account/open" class="form" method="POST">  
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name">
                            </div>
                            <button type="submit" class="btn btn-primary">Open account</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>