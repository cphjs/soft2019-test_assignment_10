<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:body>
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header d-flex align-items-center justify-content-between">
                        <h3>${account.getCustomerName()}'s account</h3>
                        <a href="/atm/exit" class="btn btn-danger">Exit</a>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <b>Current balance:</b> \$${account.getBalance()}
                        </div>

                        <form action="/atm/deposit" class="form-inline" method="POST">  
                            <input type="number" class="form-control mr-2" id="amount" name="amount" placeholder="Amount">
                            <button type="submit" class="btn btn-primary">Deposit</button>
                        </form>
                        <form action="/atm/withdraw" class="form-inline" method="POST">  
                            <input type="number" class="form-control mr-2" id="amount" name="amount" placeholder="Amount">
                            <button type="submit" class="btn btn-primary">Withdraw</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>