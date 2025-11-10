import React, { useEffect, useState } from "react";
// import { holdings } from "../data/data";
import axios from "axios";
const Holdings = () => {
  const [allHoldings, setAllHoldings] = useState([]);
  const [data, setData] = useState(null);
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/holdings/allHoldings", {
        withCredentials: true,
      })
      .then((res) => {
        setData(res.data);
        setAllHoldings(res.data.holdings);
      });
  }, []);
  if (!data)
    return (
      <h4 style={{ textAlign: "center", marginTop: "50px" }}>Loading...</h4>
    );
  return (
    <>
      <h3 className="title">Holdings ({allHoldings.length})</h3>

      <div className="order-table">
        <table>
          <thead>
            <tr>
              <th>Instrument</th>
              <th>Qty.</th>
              <th>Avg. cost</th>
              <th>LTP</th>
              <th>Cur. val</th>
              <th>P&L</th>
              <th>Net chg.</th>
              <th>Day chg.</th>
            </tr>
          </thead>
          <tbody>
            {allHoldings.map((stock, index) => {
              const currval = stock.price * stock.qty;
              const profit = currval - stock.avg * stock.qty;
              const isProfit = profit >= 0;
              const profitClass = isProfit ? "profit" : "loss";

              return (
                <tr key={index}>
                  <td>{stock.name}</td>
                  <td>{stock.qty}</td>
                  <td>{stock.avg.toFixed(2)}</td>
                  <td>{stock.price.toFixed(2)}</td>
                  <td>{currval.toFixed(2)}</td>
                  <td className={profitClass}>{profit.toFixed(2)}</td>
                  <td className={profitClass}>{stock.net}</td>
                  <td className={stock.isLoss ? "loss" : "profit"}>
                    {stock.day}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div className="row totals-row">
        <div className="col total-box">
          <h4 className="value">₹ {data.totalInvestment.toFixed(2)}</h4>
          <p className="label">Total investment</p>
        </div>

        <div className="col total-box">
          <h4 className="value">₹ {data.currentValue.toFixed(2)}</h4>
          <p className="label">Current value</p>
        </div>

        <div className="col total-box">
          <h4 className={`value ${data.profit >= 0 ? "profit" : "loss"}`}>
            ₹ {data.profit.toFixed(2)} ({data.profitPercentage.toFixed(2)}%)
          </h4>
          <p className="label">P&L</p>
        </div>
      </div>
    </>
  );
};

export default Holdings;
